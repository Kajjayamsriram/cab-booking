# cab-booking
Cab booking application using Java springboot

### commands to run the container using root
```
yum install git docker -y
systemctl start docker
systemctl enable docker
git clone https://github.com/Kajjayamsriram/cab-booking.git
cd cab-booking; docker build -t cab-image:v1 .
docker run -d --name cab-cont -p 1111:8080 cab-image:v1
```
### commands to install jenkins
```
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/rpm-stable/jenkins.repo
yum install java-21-amazon-corretto jenkins -y
systemctl enable jenkins
systemctl start jenkins
systemctl status jenkins
```

## alpine images results and usage
- Using alpine images reduced my image size to 80 MB
- Normal image took 343MB with alpine it's reduced to 263MB 
- For using alpine image for this project copy src/ and pom.xml to alpineDockerfile directory.
