import { Button, Modal } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { useState } from "react";
import axios from "axios";
import { UpdateInfo } from "../../common/types/propType";
import { getCookie } from "../../common/functions/cookie";
import { useDaumPostcodePopup } from 'react-daum-postcode';

interface InfoProps {
    info: UpdateInfo["info"];
    setInfo: React.Dispatch<any>;
    show: boolean;
    onHide: Function;
    setShow: React.Dispatch<any>;
}

function Editor({ info, setInfo, show, setShow, onHide }: InfoProps) {
    const [data, setData] = useState(info);

    const CURRENT_URL =
        "https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
    const open = useDaumPostcodePopup(CURRENT_URL);

    const handleComplete = (result: any) => {
        let fullAddress = result.address;
        let extraAddress = "";

        if (result.addressType === "R") {
            if (result.bname !== "") {
                extraAddress += result.bname;
            }
            if (result.buildingName !== "") {
                extraAddress +=
                    extraAddress !== ""
                        ? `, ${result.buildingName}`
                        : result.buildingName;
            }
            fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
        }
        setData({ ...data, location: fullAddress });
    };

    const handleClick = () => {
        open({ onComplete: handleComplete });
    };

    const handleSave = async () => {
        const url = `/api/v1/job/${info.id}`;
        const accessToken = getCookie("accessToken");
        try {
            await axios({
                method: "put",
                url: url,
                data,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            setShow(false);
        } catch (err) {
            console.error("????????????");
        }
    };
    const handleClose = () => setShow(false);

    const onSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const value = e.target.value;
        e.currentTarget.ariaLabel === "RequiredCareer"
            ? setData({ ...data, requiredCareer: value })
            : setData({ ...data, huntingSite: value });
    };

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        switch (e.currentTarget.ariaLabel) {
            case "CompanyName":
                setData({ ...data, companyName: value });
                break;
            case "link":
                setData({ ...data, link: value });
                break;
            case "Salary":
                setData({ ...data, salary: value });
                break;
            case "Date":
                setData({ ...data, applyDate: value });
                break;
            case "JobPosition":
                setData({ ...data, position: value });
                break;
            case "Note":
                setData({ ...data, note: value });
                break;
            case "Employees":
                setData({ ...data, employeesNumber: value });
                break;
            default:
                console.error("????????? ???????????????");
        }
    };
    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>?????? ?????? ??????</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon1">
                            ?????????
                        </InputGroup.Text>
                        <Form.Control
                            placeholder={info.companyName}
                            aria-label="CompanyName"
                            aria-describedby="basic-addon1"
                            onChange={onChange}
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon2">
                            ??????????????? ??????
                        </InputGroup.Text>
                        <Form.Control
                            placeholder={info.link}
                            aria-label="link"
                            aria-describedby="basic-addon2"
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon2">
                            ??????????????? ??????
                        </InputGroup.Text>
                        <Form.Select
                            aria-label="HuntingSite"
                            defaultValue={info.huntingSite}
                            onSelect={onSelect}
                        >
                            <option disabled>?????????????????? ???????????????</option>
                            <option value="?????????">?????????</option>
                            <option value="??????">??????</option>
                            <option value="????????????">????????????</option>
                            <option value="????????????">????????????</option>
                            <option value="????????????">????????????</option>
                        </Form.Select>
                    </InputGroup>

                    <Form.Label htmlFor="basic-url">????????????</Form.Label>
                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon3">
                            ?????? ??????
                        </InputGroup.Text>
                        <Form.Control
                            placeholder={info.position}
                            id="basic-url"
                            aria-label="JobPosition"
                            aria-describedby="basic-addon3"
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon2">
                            ?????? ???
                        </InputGroup.Text>
                        <Form.Control
                            placeholder={info.employeesNumber}
                            aria-label="Employees"
                            aria-describedby="basic-addon2"
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text id="basic-addon2">
                            ?????? ??????
                        </InputGroup.Text>
                        <Form.Select
                            aria-label="RequiredCareer"
                            defaultValue={info.requiredCareer}
                            onSelect={onSelect}
                        >
                            <option disabled>?????? ????????? ???????????????</option>
                            <option value="????????????">????????????</option>
                            <option value="??????">??????</option>
                            <option value="??????">??????</option>
                            <option value="1??????">1??????</option>
                            <option value="3??????">3??????</option>
                            <option value="5??????">5??????</option>
                        </Form.Select>
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text>??????</InputGroup.Text>
                        <Form.Control
                            aria-label="Salary"
                            placeholder={info.salary}
                        />
                        <InputGroup.Text>??????</InputGroup.Text>
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text>????????? ??????</InputGroup.Text>
                        <Form.Control
                            type="text"
                            value={data.location}
                            aria-label="Location"
                            onChange={onChange}
                            onClick={handleClick}
                            readOnly
                            className="cursor-pointer"
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text>?????????</InputGroup.Text>
                        <Form.Control
                          type='date'
                            aria-label="Date"
                            placeholder={info.applyDate}
                        />
                    </InputGroup>

                    <InputGroup>
                        <InputGroup.Text>??????</InputGroup.Text>
                        <Form.Control as="textarea" aria-label="Note" />
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        ??????
                    </Button>
                    <Button variant="primary" onClick={handleSave}>
                        ??????
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default Editor;
