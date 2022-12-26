const mongoose = require("mongoose");
const { Schema } = mongoose;

const roomSchema = new Schema({
    name: {
        type: String,
        required: true,
    },
    standardPeople: {
        type: Number,
        required: true,
    },
    minPeople: {
        type: Number,
        required: true,
    },
    maxPeople: {
        type: Number,
        required: true,
    },
    size: {
        type: Number,
        required: true,
    },
    countOfBeds: {
        type: Number,
        required: true,
    },
    description: {
        type: String,
        required: true,
    },
});

module.exports = mongoose.model("Room", roomSchema);
