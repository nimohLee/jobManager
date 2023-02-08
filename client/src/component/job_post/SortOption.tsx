interface SortOption {
    get accurancy(): string;
    get regDate(): string;
    get editDate(): string;
    get closingDate(): string;
    get applyCount(): string;
}

export class SaraminOption implements SortOption {
  private static instance:SaraminOption;
  private constructor(){}
  public static getInstance():SaraminOption{
    if(!SaraminOption.instance){
      SaraminOption.instance = new SaraminOption();
    }
    return SaraminOption.instance;
  }

  get relation(): string {
    return 'relation';
  }
  get accurancy(): string {
    return 'accuracy';
  }
  get regDate(): string {
    return 'reg_dt';
  }
  get editDate(): string {
    return 'edit_dt';
  }
  get closingDate(): string {
    return 'closing_dt';
  }
  get employCount(): string {
    return 'employ_cnt';
  }
  get applyCount(): string {
    return 'apply_cnt';
  }
}

export class JobKoreaOption implements SortOption{
  private static instance:JobKoreaOption;
  private constructor(){}
  public static getInstance():JobKoreaOption{
    if(!JobKoreaOption.instance){
      JobKoreaOption.instance = new JobKoreaOption();
    }
    return JobKoreaOption.instance;
  }
  
  get accurancy(): string {
    return 'ExactDesc';
  }
  get regDate(): string {
    return 'RegDtDesc';
  }
  get editDate(): string {
    return 'EditDtDesc';
  }
  get closingDate(): string {
    return 'ApplyCloseDtAsc';
  }
  get applyCount(): string {
    return 'ApplicantDesc';
  }
  get readCount(): string {
    return 'ReadCntDesc';
  }
}

export class IncruitOption implements SortOption{
  private static instance:IncruitOption;
  private constructor(){}
  public static getInstance():IncruitOption{
    if(!IncruitOption.instance){
      IncruitOption.instance = new IncruitOption();
    }
    return IncruitOption.instance;
  }

  get accurancy(): string {
    return 'rank';
  }
  get regDate(): string {
    return 'reg';
  }
  get editDate(): string {
    return 'mod';
  }
  get closingDate(): string {
    return 'invite';
  }
  get applyCount(): string {
    return 'applycnt';
  }
  get readCount(): string {
    return 'viewcnt';
  }
}