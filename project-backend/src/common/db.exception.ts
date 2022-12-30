import {
  ExceptionFilter,
  Catch,
  ArgumentsHost,
  HttpStatus,
} from '@nestjs/common';
import { PrismaClientKnownRequestError } from '@prisma/client/runtime';
import { Request, Response } from 'express';

@Catch(PrismaClientKnownRequestError)
export class DatabaseExceptionFilter implements ExceptionFilter {
  catch(exception: PrismaClientKnownRequestError, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();
    const request = ctx.getRequest<Request>();

    switch (exception.code) {
      case 'P2025': {
        response.status(HttpStatus.NOT_FOUND).json({
          statusCode: HttpStatus.NOT_FOUND,
          message: `Item not found.`,
          timestamp: new Date().toISOString(),
          path: request.url,
        });
        return;
      }
      case 'P2002': {
        response.status(HttpStatus.BAD_REQUEST).json({
          statusCode: HttpStatus.BAD_REQUEST,
          message: `Record with some parameter already exists.`,
          timestamp: new Date().toISOString(),
        });
        return;
      }
      default: {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
          statusCode: HttpStatus.INTERNAL_SERVER_ERROR,
          message: 'Internal server error. please notify seminar instructor',
          timestamp: new Date().toISOString(),
        });
        return;
      }
    }
  }
}
