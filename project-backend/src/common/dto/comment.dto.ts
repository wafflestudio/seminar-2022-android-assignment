import { ApiProperty } from '@nestjs/swagger';
import { Comment, User } from '@prisma/client';
import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
import { mapUserSchema, UserDto } from 'src/common/dto/user.dto';

export class CommentDto {
  @IsNumber()
  @IsNotEmpty()
  @ApiProperty({ description: '댓글의 고유 id 입니다' })
  id: number;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ description: '댓글의 메세지 입니다' })
  message: string;

  @IsNotEmpty()
  @ApiProperty({ description: '댓글의 작성자 입니다' })
  author: UserDto;

  @ApiProperty({ description: '댓글이 작성된 시각 입니다' })
  createdAt: Date;
}

export function mapCommentSchema(
  schema: Comment,
  author: User | undefined,
): CommentDto {
  return {
    id: schema.id,
    message: schema.message,
    author: mapUserSchema(author),
    createdAt: schema.createdAt,
  };
}
