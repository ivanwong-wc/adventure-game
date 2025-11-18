#!/bin/bash
echo "正在用系統 Node 打包 Vue ... 土豪金發光中..."

cd src/main/Vue

# 用你系統的 npm（絕對不會錯！）
/usr/local/bin/npm install
/usr/local/bin/npm run build:game

# 自動把 dist 複製到 Spring Boot 靜態目錄
rm -rf ../resources/static/*
cp -r dist/* ../resources/static/

echo "前端打包完成！已放到 src/main/resources/static"
echo "現在執行 Maven 啟動後端就行了！"