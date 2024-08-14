#!/bin/bash

# Exit the script on any error
set -e

# Update package index
echo "Updating package index..."
sudo yum update -y

# Install Java 21
echo "Installing Java 21..."
sudo yum install -y java-21-amazon-corretto

# Install Maven
echo "Installing Maven..."
sudo yum install -y maven

# Install Python and other dependencies
echo "Installing Python and other dependencies..."
sudo yum install -y python3 python3-pip

# Install C and C++ compilers
echo "Installing C and C++ compilers..."
sudo yum groupinstall -y "Development Tools"

# Install Nginx
echo "Installing Nginx..."
sudo yum install -y nginx

# Install Node.js (for JavaScript runtime, if needed)
echo "Installing Node.js..."
sudo yum install -y nodejs

# Download and Install .NET 8.0 SDK
echo "Downloading and Installing .NET 8.0 SDK..."
wget https://download.visualstudio.microsoft.com/download/pr/6f3b1c94-6892-4974-8f76-f5cfc5016217/88d5e8ad764946e05a71ed6f6ec7c1a4/dotnet-sdk-8.0.100-linux-x64.rpm
sudo yum localinstall -y dotnet-sdk-8.0.100-linux-x64.rpm

# Configure Nginx to forward traffic from port 82 to port 8088
echo "Configuring Nginx..."
cat <<EOL | sudo tee /etc/nginx/conf.d/sortingvisualizer.conf
server {
    listen 82;
    server_name localhost;

    location / {
        proxy_pass http://localhost:8088;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOL

# Restart Nginx to apply the new configuration
echo "Restarting Nginx..."
sudo systemctl restart nginx

# Set Java 21 environment variables locally for this application
export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
export PATH=$JAVA_HOME/bin:$PATH

# Build the Java application using Java 21
echo "Building the Java application..."
mvn clean package

# Change directory to target
cd target

# Run the JAR file on port 8088 using nohup
echo "Running the Java application on port 8088..."
nohup java -jar sortingvisualizer-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

echo "Setup complete and application is running."
