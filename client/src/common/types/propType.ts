import { PrimarySkill } from './literalType';

export interface UpdateInfo {
    info: {
        id? : string;
        companyName? : string;
        huntingSite? : string;
        position?: string;
        employeesNumber?: string;
        location?: string;
        salary?: string;
        applyDate?: string;
        link?: string;
        requiredCareer?: string;
        result?: string;
        primarySkill? : PrimarySkill;
        note?: string;
    }
};