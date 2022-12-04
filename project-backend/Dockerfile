FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN yarn
RUN npx prisma generate
RUN yarn build

FROM node:18-slim

RUN apt-get update
RUN apt-get install -y openssl

ARG ARG_JWT_SECRET_KEY
ENV JWT_SECRET_KEY=${ARG_JWT_SECRET_KEY}

WORKDIR /app
ENV NODE_ENV production
COPY --from=builder /app ./
CMD ["yarn", "start:prod"]
