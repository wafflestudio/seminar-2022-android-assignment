import { Injectable } from '@nestjs/common';
import { Comment, Prisma } from '@prisma/client';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class CommentService {
  constructor(private prisma: PrismaService) {}

  async createComment(data: Prisma.CommentCreateInput): Promise<Comment> {
    return this.prisma.comment.create({
      data,
    });
  }

  async deleteComment(where: Prisma.CommentWhereUniqueInput): Promise<Comment> {
    return this.prisma.comment.delete({
      where,
    });
  }

  async commentWithAuthor(
    commentWhereUniqueInput: Prisma.CommentWhereUniqueInput,
  ) {
    return this.prisma.comment.findFirstOrThrow({
      where: commentWhereUniqueInput,
      include: {
        author: true,
      },
    });
  }
}
