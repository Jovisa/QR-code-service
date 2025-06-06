#!/bin/bash

# === Config ===
JAR_NAME="qrcode-service-0.0.1-SNAPSHOT.jar"
REMOTE_USER="ubuntu"
REMOTE_HOST="13.53.198.101"
REMOTE_DIR="/home/ubuntu/"
KEY_PATH="QrCodeServiceAwsKey.pem"
LOCAL_JAR_PATH="build/libs/$JAR_NAME"

# === Build ===
echo "👉 Building application..."
./gradlew bootJar || { echo "❌ Build failed"; exit 1; }

# === Remove old JAR on EC2 ===
echo "🧹 Removing old JAR on EC2..."
ssh -i "$KEY_PATH" "$REMOTE_USER@$REMOTE_HOST" "rm -f $REMOTE_DIR/$JAR_NAME" || { echo "❌ Failed to delete old JAR"; exit 1; }
#ssh -i "$KEY_PATH" "$REMOTE_USER@$REMOTE_HOST" "rm -f $REMOTE_DIR/$JAR_NAME $REMOTE_DIR/app.log" || { echo "❌ Failed to delete old files"; exit 1; }

# === Transfer ===
echo "📤 Copying JAR to EC2..."
# Print the actual command for debugging/logging purposes
echo ">> scp -i \"$KEY_PATH\" \"$LOCAL_JAR_PATH\" \"$REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/\""

scp -i "$KEY_PATH" "$LOCAL_JAR_PATH" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/" || { echo "❌ SCP failed"; exit 1; }

# === SSH and Run ===
echo "🚀 Connecting to EC2 and running the app..."
ssh -i "$KEY_PATH" "$REMOTE_USER@$REMOTE_HOST" <<EOF
  cd "$REMOTE_DIR"
  echo "Running: java -jar $JAR_NAME"
  nohup java -jar "$JAR_NAME" > app.log 2>&1 &
  echo "✅ App is starting in background. Logs: app.log"
EOF

# start app in the background:   nohup java -jar "$JAR_NAME" > app.log 2>&1 &
# watch the logs live (if it runs in the background): tail -f app.log
# kill the process: sudo fuser -k 9090/tcp


# === Restart app remotely ===
#echo "🔁 Restarting app on EC2..."
#ssh -i "$KEY_PATH" "$REMOTE_USER@$REMOTE_HOST" << EOF
#  pkill -f "$JAR_NAME"
#  nohup java -jar "$REMOTE_DIR/$JAR_NAME" > "$REMOTE_DIR/app.log" 2>&1 &
#  echo "✅ App restarted"
#EOF
