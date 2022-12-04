import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { User } from '@prisma/client';
import * as bcrypt from 'bcrypt';
import { UserService } from 'src/user/user.service';

@Injectable()
export class AuthService {
  constructor(
    private readonly userService: UserService,
    private readonly jwtService: JwtService,
  ) {}

  async validateUser(
    username: string,
    password: string,
  ): Promise<Omit<User, 'password'>> {
    const user = await this.userService.findOneByUsername(username);
    if (user && bcrypt.compareSync(password, user.password)) {
      const { password, ...result } = user;
      return result;
    }
    return null;
  }

  async login(user: {
    username: string;
    password: string;
  }): Promise<{ accessToken: string }> {
    return {
      accessToken: this.jwtService.sign(user),
    };
  }

  async signUp(user: {
    username: string;
    password: string;
  }): Promise<{ accessToken: string }> {
    const { username, password } = user;
    const hash = bcrypt.hashSync(password, 10);
    await this.userService.createUser({ username, password: hash });
    return {
      accessToken: this.jwtService.sign({ username: username }),
    };
  }
}
