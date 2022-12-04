import { Injectable } from '@nestjs/common';
import { Post, Prisma } from '@prisma/client';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class PostService {
  constructor(private prisma: PrismaService) {}

  async postWithCommentAndAuthor(
    postWhereUniqueInput: Prisma.PostWhereUniqueInput,
  ) {
    return this.prisma.post.findFirstOrThrow({
      where: postWhereUniqueInput,
      include: {
        author: true,
        comments: {
          include: {
            author: true,
          },
        },
      },
    });
  }

  async postWithAuthor(postWhereUniqueInput: Prisma.PostWhereUniqueInput) {
    return this.prisma.post.findFirstOrThrow({
      where: postWhereUniqueInput,
      include: {
        author: true,
      },
    });
  }

  async postsWithCommentAndAuthor(params: {
    take?: number;
    skip?: number;
    cursor?: Prisma.PostWhereUniqueInput;
    where?: Prisma.PostWhereInput;
    orderBy?: Prisma.PostOrderByWithRelationInput;
  }) {
    const { take, skip, cursor, where, orderBy } = params;
    return this.prisma.post.findMany({
      take,
      skip,
      cursor,
      where,
      orderBy,
      include: { author: true, comments: { include: { author: true } } },
    });
  }

  async createPost(data: Prisma.PostCreateInput): Promise<Post> {
    return this.prisma.post.create({
      data,
    });
  }

  async deletePost(where: Prisma.PostWhereUniqueInput): Promise<Post> {
    return this.prisma.post.delete({
      where,
    });
  }
}
