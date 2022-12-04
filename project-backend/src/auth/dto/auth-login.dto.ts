import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';

export class AuthLoginRequest {
  @IsNotEmpty()
  @IsString()
  @ApiProperty({
    example: 'sanggggg@wafflestudio.com',
    description: '유저 이메일',
  })
  username: string;

  @IsNotEmpty()
  @IsString()
  @ApiProperty({
    example: 'qwer1234',
    description: '유저 비밀번호',
  })
  password: string;
}

export class AuthLoginResult {
  @ApiProperty({
    description: '로그인에 필요한 token 입니다.',
  })
  accessToken: string;
}
