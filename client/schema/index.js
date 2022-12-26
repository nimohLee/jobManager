const mongoose = require("mongoose");

const connect = () => {
    if (process.env.NODE_ENV !== "production") {
        mongoose.set("debug", true);
    }
    mongoose.connect(
        "mongodb://nimoh:1234@localhost:27017/admin", // 이거를 서버로 돌려야함-> 아니면 내 컴퓨터에서 몽고 계속 켜놔야,,,
        {
            dbName: "hotel",
            useNewUrlParser: true,
        },
        (error) => {
            if (error) {
                console.log("몽고디비 연결 에러", error);
            } else {
                console.log("몽고디비 연결 성공");
            }
        }
    );
};

mongoose.connection.on("error", (error) => {
    console.error("몽고디비 연결 에러", error);
});
mongoose.connection.on("disconnected", () => {
    console.error("몽고디비 연결이 끊겼습니다. 연결을 재시도합니다.");
    connect();
});

module.exports = connect;
