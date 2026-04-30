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
