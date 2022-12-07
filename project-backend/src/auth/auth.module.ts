import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { PassportModule } from '@nestjs/passport';
import { JwtStrategy } from 'src/auth/jwt.strategy';
import { LocalStrategy } from 'src/auth/local.strategy';
import { JWT_SECRET_KEY } from 'src/common/env';
import { UserModule } from 'src/user/user.module';
import { AuthService } from './auth.service';

@Module({
  imports: [
    UserModule,
    PassportModule,
    JwtModule.register({
      secret: JWT_SECRET_KEY,
      signOptions: { expiresIn: '5h' },
    }),
  ],
  providers: [AuthService, LocalStrategy, JwtStrategy],
  exports: [AuthService],
})
export class AuthModule {}
