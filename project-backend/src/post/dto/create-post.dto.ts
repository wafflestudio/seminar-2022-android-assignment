import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';
import { PostDto } from 'src/common/dto/post.dto';

export class CreatePostRequest {
  @IsString()
  @IsNotEmpty()
  @ApiProperty({ example: '포스트 내용', description: '게시글의 내용물입니다' })
  content: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ example: '포스트 제목', description: '게시글의 제목입니다' })
  title: string;
}

export class CreatePostResponse {
  @ApiProperty({ description: '생성된 게시글 입니다' })
  result: PostDto;
}
