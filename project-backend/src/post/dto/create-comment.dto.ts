import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';
import { PostDto } from 'src/common/dto/post.dto';

export class CreateCommentRequest {
  @IsString()
  @IsNotEmpty()
  @ApiProperty({ description: '댓글의 내용물입니다' })
  message: string;
}

export class CreateCommentResponse {
  @ApiProperty({ description: '댓글이 달린 게시글 입니다' })
  result: PostDto;
}
