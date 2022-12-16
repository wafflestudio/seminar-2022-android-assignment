import { ApiProperty } from '@nestjs/swagger';
import { Comment, Post, User } from '@prisma/client';
import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
import { CommentDto, mapCommentSchema } from 'src/common/dto/comment.dto';
import { mapUserSchema, UserDto } from 'src/common/dto/user.dto';

export class PostDto {
  @IsNumber()
  @IsNotEmpty()
  @ApiProperty({ description: '게시글의 고유 id 입니다' })
  id: number;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ description: '게시글의 내용물 입니다' })
  content: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ description: '게시글의 제목 입니다' })
  title: string;

  @IsNotEmpty()
  @ApiProperty({ description: '게시글의 저자 입니다' })
  author: UserDto;

  @ApiProperty({
    type: [CommentDto],
    description: '게시글에 달린 댓글들 입니다',
  })
  comments: CommentDto[];

  @ApiProperty()
  @ApiProperty({ description: '게시글이 만들어진 시각 입니다' })
  createdAt: Date;
}

export function mapPostSchema(
  schema: Post,
  author: User | undefined,
  comments: (Comment & { author: User })[] | undefined,
): PostDto {
  return {
    id: schema.id,
    content: schema.content || '',
    title: schema.title,
    author: mapUserSchema(author),
    comments: comments.map((it) => mapCommentSchema(it, it.author)) || [],
    createdAt: schema.createdAt,
  };
}
