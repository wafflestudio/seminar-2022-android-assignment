import { Module } from '@nestjs/common';
import { CommentService } from 'src/post/comment.service';
import { PrismaModule } from 'src/prisma/prisma.module';
import { PostController } from './post.controller';
import { PostService } from './post.service';

@Module({
  imports: [PrismaModule],
  controllers: [PostController],
  providers: [PostService, CommentService],
})
export class PostModule {}
