FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN yarn
RUN npx prisma generate
RUN yarn build

FROM node:18-slim

RUN apt-get update
RUN apt-get install -y openssl

WORKDIR /app
ENV NODE_ENV production
COPY --from=builder /app ./
CMD ["yarn", "start:prod"]
