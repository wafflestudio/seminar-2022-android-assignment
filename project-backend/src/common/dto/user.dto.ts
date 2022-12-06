import { ApiProperty } from '@nestjs/swagger';
import { User } from '@prisma/client';
import { IsInt, IsNotEmpty, IsString } from 'class-validator';

export class UserDto {
  @IsInt()
  @IsNotEmpty()
  @ApiProperty({ description: '사용자의 고유 id 입니다' })
  id: number;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({ description: '사용자의 이름 입니다' })
  username: string;
}

export function mapUserSchema(schema: User): UserDto {
  return {
    id: schema.id,
    username: schema.username,
  };
}
