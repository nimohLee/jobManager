"use strict";

/**
 * ----------------------Calander----------------------
 */

const dateClass = new Date();
const year = dateClass.getFullYear();
const month =
    dateClass.getMonth() < 10
        ? `0${dateClass.getMonth() + 1}`
        : dateClass.getMonth() + 1;
const date =
    dateClass.getDate() < 10
        ? `0${dateClass.getDate() + 1}`
        : dateClass.getDate() + 1;
/* 월 : 1 */
const day = dateClass.getDay();
dateClass.get
console.log(day);
