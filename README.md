# CS166 Final Project

## Authors: Brandon Yi and Moses Park

---

# Deploying

## Dependencies

1. Java (and java.awt)
2. Postgre

*Note: For remote connections, enable X Forwarding*

## Start the Application

```bash
git clone https://github.com/bypie5/CS166_final_project.git
cd CS166_final_project

cd code

# Deploy postgresql server
cd postgresql
source ./createPostgreDB.sh
source ./startPostgreDB.sh

# Start java application
cd ../java
./compile.sh

# source ./run.sh byi006_DB 5025 byi006
source ./run.sh $USER"_DB" $PGPORT $USER
```


