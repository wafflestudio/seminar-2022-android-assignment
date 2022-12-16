import { Body, Controller, Post, Request, UseGuards } from '@nestjs/common';
import {
  ApiBadRequestResponse,
  ApiOkResponse,
  ApiOperation,
  ApiUnauthorizedResponse,
} from '@nestjs/swagger';
import { AuthService } from 'src/auth/auth.service';
import { AuthLoginRequest, AuthLoginResult } from 'src/auth/dto/auth-login.dto';
import {
  AuthSignUpRequest,
  AuthSignUpResult,
} from 'src/auth/dto/auth-sign-up.dto';
import { LocalAuthGuard } from 'src/auth/local-auth.guard';

@Controller()
export class AppController {
  constructor(private readonly authService: AuthService) {}

  @UseGuards(LocalAuthGuard)
  @ApiOperation({
    summary: '유저 로그인',
    description:
      '유저 로그인을 시도한다, 로그인 성공 시 인증된 토큰을 발급한다',
  })
  @ApiOkResponse({
    type: AuthLoginResult,
    description: '비밀번호, 아이디 인증 성공',
  })
  @ApiUnauthorizedResponse({
    description: '비밀번호, 아이디 인증 실패',
  })
  @Post('auth/login')
  async login(
    @Request() req,
    @Body() payload: AuthLoginRequest,
  ): Promise<AuthLoginResult> {
    return this.authService.login(req.user);
  }

  @ApiOperation({
    summary: '유저 가입',
    description: '유저 가입을 시도한다, 가입 성공 시 인증된 토큰을 발급한다',
  })
  @ApiOkResponse({
    type: AuthSignUpResult,
    description: '가입 성공',
  })
  @ApiBadRequestResponse({
    description: '이미 동일한 유저이름이 존재한다면 실패합니다.',
  })
  @Post('auth/sign-up')
  async signUp(@Body() payload: AuthSignUpRequest): Promise<AuthSignUpResult> {
    const { username, password } = payload;
    return this.authService.signUp({ username, password });
  }
}
