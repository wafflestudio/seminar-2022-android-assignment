import {
  Body,
  Controller,
  Delete,
  Get,
  HttpCode,
  HttpException,
  HttpStatus,
  Param,
  ParseIntPipe,
  Post,
  Query,
  Request,
  UseGuards,
} from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiResponse } from '@nestjs/swagger';
import { Comment, Post as PostSchema, User } from '@prisma/client';
import { JwtAuthGuard } from 'src/auth/jwt-auth.guard';
import { mapPostSchema } from 'src/common/dto/post.dto';
import { CommentService } from 'src/post/comment.service';
import {
  CreateCommentRequest,
  CreateCommentResponse,
} from 'src/post/dto/create-comment.dto';
import {
  CreatePostRequest,
  CreatePostResponse,
} from 'src/post/dto/create-post.dto';
import {
  GetAllPostQuery,
  GetAllPostResponse,
} from 'src/post/dto/get-all-post.dto';
import { GetPostResponse } from 'src/post/dto/get-post.dto';
import { PostService } from 'src/post/post.service';

@Controller('post')
@UseGuards(JwtAuthGuard)
@ApiBearerAuth()
export class PostController {
  constructor(
    private readonly postService: PostService,
    private readonly commentService: CommentService,
  ) {}

  @Post()
  @ApiOperation({ summary: '게시글 생성' })
  @ApiResponse({ type: CreatePostResponse })
  async createPost(
    @Request() req: { user: User },
    @Body() payload: CreatePostRequest,
  ): Promise<CreatePostResponse> {
    const user = req.user;
    const result = await this.postService.createPost({
      ...payload,
      author: {
        connect: {
          id: user.id,
        },
      },
    });
    return {
      result: mapPostSchema(result, user, []),
    };
  }

  @Get(':postId')
  @ApiOperation({ summary: '단일 게시글 조회' })
  @ApiResponse({ type: GetPostResponse })
  async getPost(
    @Param(
      'postId',
      new ParseIntPipe({ errorHttpStatusCode: HttpStatus.NOT_ACCEPTABLE }),
    )
    postId: number,
  ): Promise<GetPostResponse> {
    const fetchResult = await this.postService.postWithCommentAndAuthor({
      id: postId,
    });
    return {
      result: mapPostSchema(
        fetchResult,
        fetchResult.author,
        fetchResult.comments,
      ),
    };
  }

  @Get()
  @ApiOperation({ summary: '모든 게시글 페이지로 조회' })
  @ApiResponse({ type: GetAllPostResponse })
  async getAllPosts(
    @Query() query: GetAllPostQuery,
  ): Promise<GetAllPostResponse> {
    let fetchResult: (PostSchema & {
      author: User;
      comments: (Comment & { author: User })[];
    })[];
    const pageCount = query.count;
    const pageCursor = query.cursor;
    if (pageCursor) {
      fetchResult = await this.postService.postsWithCommentAndAuthor({
        take: pageCount,
        skip: 1,
        cursor: { id: pageCursor },
        orderBy: { id: 'desc' },
      });
    } else {
      fetchResult = await this.postService.postsWithCommentAndAuthor({
        take: pageCount,
        orderBy: { id: 'desc' },
      });
    }
    const dtos = fetchResult.map((x) => mapPostSchema(x, x.author, x.comments));
    return {
      posts: dtos,
      nextCursor: fetchResult[pageCount - 1]?.id,
    };
  }

  @Delete('/:postId')
  @ApiOperation({ summary: '게시글 삭제' })
  @HttpCode(204)
  async deletePost(
    @Request() req: { user: User },
    @Param(
      'postId',

      new ParseIntPipe({ errorHttpStatusCode: HttpStatus.NOT_ACCEPTABLE }),
    )
    postId: number,
  ) {
    const user = req.user;
    const post = await this.postService.postWithAuthor({ id: postId });
    if (post) {
      if (post.author.id === user.id) {
        await this.postService.deletePost({ id: postId });
        return '';
      } else {
        throw new HttpException(
          {
            status: HttpStatus.FORBIDDEN,
            error: 'you are not owner of this post',
          },
          HttpStatus.FORBIDDEN,
        );
      }
    } else {
    }
  }

  @Post('/:postId/comment')
  @ApiOperation({ summary: '게시글에 댓글 생성' })
  async createComment(
    @Request() req: { user: User },
    @Param(
      'postId',

      new ParseIntPipe({ errorHttpStatusCode: HttpStatus.NOT_ACCEPTABLE }),
    )
    postId: number,
    @Body() payload: CreateCommentRequest,
  ): Promise<CreateCommentResponse> {
    const user = req.user;
    const _ = await this.commentService.createComment({
      ...payload,
      post: {
        connect: {
          id: postId,
        },
      },
      author: {
        connect: {
          id: user.id,
        },
      },
    });
    const result = await this.postService.postWithCommentAndAuthor({
      id: postId,
    });
    return {
      result: mapPostSchema(result, result.author, result.comments),
    };
  }

  @Delete('/comment/:commentId')
  @ApiOperation({ summary: '댓글 삭제' })
  @HttpCode(204)
  async deleteComment(
    @Request() req: { user: User },
    @Param(
      'commentId',

      new ParseIntPipe({ errorHttpStatusCode: HttpStatus.NOT_ACCEPTABLE }),
    )
    commentId: number,
  ) {
    const user = req.user;
    const comment = await this.commentService.commentWithAuthor({
      id: commentId,
    });
    if (comment) {
      if (comment.author.id === user.id) {
        await this.commentService.deleteComment({ id: commentId });
        return '';
      } else {
        throw new HttpException(
          {
            status: HttpStatus.FORBIDDEN,
            error: 'you are not owner of this comment',
          },
          HttpStatus.FORBIDDEN,
        );
      }
    } else {
    }
  }
}
