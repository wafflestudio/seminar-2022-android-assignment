import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import { IsInt, IsNotEmpty, IsOptional } from 'class-validator';
import { PostDto } from 'src/common/dto/post.dto';

export class GetAllPostQuery {
  @IsOptional()
  @IsInt()
  @Type(() => Number)
  @ApiProperty({
    description:
      '게시글 페이지를 찾기 위한 커서 입니다, 첫 페이지 로드 시 꼭 입력할 필요 없습니다. 첫 페이지 조회 이후 다음 페이지를 가져올 때 이전 페이지의 응답으로 온 커서를 넘겨줘야 합니다.',
    required: false,
  })
  cursor: number | undefined;

  @IsNotEmpty()
  @IsInt()
  @Type(() => Number)
  @ApiProperty({
    description: '한번에 가져올 게시글의 개수 입니다',
  })
  count: number;
}

export class GetAllPostResponse {
  @ApiProperty({ type: [PostDto], description: '가져온 게시글들 입니다' })
  posts: PostDto[];

  @IsNotEmpty()
  @IsOptional()
  @ApiProperty({
    required: false,
    description:
      '조회된 페이지의 다음 페이지를 조회할 때 사용하는 커서입니다. 만약 더 이상 게시글이 없다면 내려오지 않습니다',
  })
  nextCursor: number | undefined;
}
