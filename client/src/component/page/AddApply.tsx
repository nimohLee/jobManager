import { Button, FormControlProps, Modal } from "react-bootstrap";
import { useDaumPostcodePopup } from 'react-daum-postcode';
import { useState, useEffect } from "react";
import axios from "axios";
import { UpdateInfo } from "../../common/types/propType";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";

function AddApply() {
    const [data, setData] = useState<UpdateInfo["info"]>();
    const [validated, setValidated] = useState(false);
    useEffect(()=>{
        setData({...data,huntingSite:"사람인",requiredCareer:"경력무관"});
    },[]);

    const CURRENT_URL =
    'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
const open = useDaumPostcodePopup(CURRENT_URL);

const handleComplete = (result:any) => {
let fullAddress = result.address;
let extraAddress = '';

if (result.addressType === 'R') {
  if (result.bname !== '') {
    extraAddress += result.bname;
  }
  if (result.buildingName !== '') {
    extraAddress += extraAddress !== '' ? `, ${result.buildingName}` : result.buildingName;
  }
  fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
}
setData({...data,location:fullAddress});
};

const handleClick = () => {
open({ onComplete: handleComplete });
};
    const handleSubmit = async (e:React.FormEvent<HTMLFormElement>) => {
        const form = e.currentTarget;
        e.preventDefault();
        if(form.checkValidity()===false){
            e.preventDefault();
            e.stopPropagation();
        };
        setValidated(true);
        const url = "/api/v1/job";
        try {
            await axios({
                method: "post",
                url: url,
                data: data,
                headers: {
                    "Content-Type": "application/json",
                },
            });
            alert("등록이 완료되었습니다.");
            window.location.href= "/";
        } catch (err) {
            console.error("에러발생");
        }
    };

    const onSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const value = e.target.value;
        e.target.ariaLabel === "RequiredCareer"
        ? setData({...data,requiredCareer:value})
        : setData({...data,huntingSite:value}); 
    }

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        switch(e.target.ariaLabel){
          case "CompanyName":
            setData({...data, companyName: value});
            break;
          case "Link":
            setData({...data, link:value});
            break;
          case "Location":
            setData({...data, location:value});
            break;
          case "Salary":
            setData({...data, salary:value});
            break;
          case "ApplyDate":
            setData({...data, applyDate:value});
            break;
          case "Position":
            setData({...data, position:value});
            break;
          case "Note":
            setData({...data, note:value});
            break;
          case "Employees":
            setData({...data, employeesNumber:value});
            break; 
          default:
            console.error("잘못된 접근입니다");
        }
    };
    return (
        <div className="flex flex-col items-center">
            <section className="w-3/4">
                <Form noValidate validated={validated} onSubmit={handleSubmit} >
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextEmail"
                    >
                        <Form.Label column sm="3">
                            회사명
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="회사명"
                                aria-label="CompanyName"
                                onChange={onChange}
                                required
                            />
                            <Form.Control.Feedback type='invalid'>회사명을 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            구직공고 주소
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="구직공고 주소"
                                aria-label="Link"
                                onChange={onChange}
                                required
                            />
                            <Form.Control.Feedback type='invalid'>구직공고의 주소를 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            구직사이트
                        </Form.Label>
                        <Col sm="7">
                            <Form.Select
                                aria-label="HuntingSite"
                                onChange={onSelectChange}
                                defaultChecked
                                defaultValue="사람인"
                                id='huntingSite'
                                required
                            >
                                <option value="사람인">사람인</option>
                                <option value="점핏">점핏</option>
                                <option value="인크루트">인크루트</option>
                                <option value="잡코리아">잡코리아</option>
                                <option value="잡플래닛">잡플래닛</option>
                            </Form.Select>
                            <Form.Control.Feedback type='invalid'>구직사이트를 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            지원 분야
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="지원 분야"
                                aria-label="Position"
                                onChange={onChange}
                                required
                            />
                            <Form.Control.Feedback type='invalid'>지원 분야를 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            직원 수
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="직원 수"
                                aria-label="Employees"
                                onChange={onChange}
                                required
                            />
                        </Col>
                        <Form.Control.Feedback type='invalid'>직원 수를 입력하세요</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            요구 경력
                        </Form.Label>
                        <Col sm="7">
                            <Form.Select
                                aria-label="RequiredCareer"
                                defaultChecked
                                defaultValue="경력무관"
                                onChange={onSelectChange}
                                required
                            >
                                <option value="경력무관">경력무관</option>
                                <option value="신입">신입</option>
                                <option value="경력">경력</option>
                                <option value="1년차">1년차</option>
                                <option value="3년차">3년차</option>
                                <option value="5년차">5년차</option>
                            </Form.Select>
                            <Form.Control.Feedback type='invalid'>요구 경력을 선택하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            연봉
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="연봉 (단위 : 만원, 알 수 없을 시 0 입력) "
                                aria-label="Salary"
                                onChange={onChange}
                                required
                            />
                            <Form.Control.Feedback type='invalid'>연봉을 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            근무지 위치
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="근무지 주소 ( 클릭 시 우편번호 검색 )"
                                aria-label="Location"
                                onChange={onChange}
                                value={data?.location}
                                onClick={handleClick}
                                readOnly
                                className='cursor-pointer'
                                required
                            />
                            <Form.Control.Feedback type='invalid'>근무지의 주소를 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            지원 일자
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control
                                type="text"
                                placeholder="지원 일자 (ex. 2022-01-26 )"
                                aria-label="ApplyDate"
                                onChange={onChange}
                                required
                            />
                            <Form.Control.Feedback type="invalid">지원 일자를 입력하세요</Form.Control.Feedback>
                        </Col>
                    </Form.Group>
                    <Form.Group
                        as={Row}
                        className="mb-3"
                        controlId="formPlaintextPassword"
                    >
                        <Form.Label column sm="3">
                            비고
                        </Form.Label>
                        <Col sm="7">
                            <Form.Control as="textarea" aria-label="Note" onChange={onChange} defaultValue=""/>
                        </Col>
                    </Form.Group>
                    <Button type="submit">지원 등록</Button>
                </Form>
            </section>
        </div>
    );
}

export default AddApply;
