#!/bin/bash

echo "👉 开始创建挂载目录..."

# 1. Nacos 目录
mkdir -p /usr/local/tools/nacos/{conf,logs,data}

# 2. PostgreSQL 目录
mkdir -p /usr/local/tools/postgresql/data

# 3. MySQL8 目录
mkdir -p /usr/local/tools/mysql8/{data,logs,conf.d}

# 4. Redis 目录
mkdir -p /usr/local/tools/redis/{data,conf}

# 5. 你的业务服务目录
mkdir -p /data/item

echo "✅ 目录创建完毕！"

# --- 处理关键文件和权限 ---

echo "👉 处理 Redis 配置文件..."
# 必须确保是一个文件，而不是目录
if [ -d "/usr/local/tools/redis/conf/redis.conf" ]; then
    echo "⚠️ 检测到 redis.conf 是一个目录，正在删除并重建为文件..."
    rm -rf /usr/local/tools/redis/conf/redis.conf
fi
# 创建一个空的 redis.conf 文件
touch /usr/local/tools/redis/conf/redis.conf

echo "👉 修复目录权限（防止 MySQL/PG 权限报错）..."
# 开放权限，确保容器内的用户能正常读写（生产环境可按需收紧权限）
chmod -R 777 /usr/local/tools/mysql8/data
chmod -R 777 /usr/local/tools/postgresql/data
chmod -R 777 /usr/local/tools/nacos

echo "🎉 初始化完成！现在可以安全地执行 docker-compose up -d 啦！"
