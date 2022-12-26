const mongoose = require('mongoose');

const { Schema } = mongoose;
const { Types: { ObjectId } } = Schema;

const reservationSchema = new Schema({
    reservator : {
        type: ObjectId,
        required: true,
        ref: 'User'
    },
    startDate : {
        type: Date,
        required: true
    },
    endDate: {
        type: Date,
        required: true
    },
    room: {
        type: String,
        required: true,
        ref: 'Room'

    },
    numberOfPeople: {
        type: Number,
    }
    }
);

module.exports = mongoose.model("Reservation", reservationSchema);