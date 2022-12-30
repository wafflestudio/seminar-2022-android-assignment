import { ApiProperty } from '@nestjs/swagger';
import { PostDto } from 'src/common/dto/post.dto';

export class GetPostResponse {
  @ApiProperty()
  result: PostDto;
}
