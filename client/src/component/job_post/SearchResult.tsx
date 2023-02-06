import React from "react";
import { Badge, ListGroup } from "react-bootstrap";
import { SearchResultData } from "../../common/types/propType";

interface PropsInfo {
    searchResult: SearchResultData;
}

function SearchResult(props: PropsInfo) {
    return (
        <div>
            {props.searchResult.map((post, i) => {
                return (
                    <ListGroup as="ol" key={i} >
                        <ListGroup.Item
                            as="li"
                            className="d-flex justify-content-between align-items-start"
                        >
                            <div className="ms-2 me-auto">
                                <div className="fw-bold">{post.title}</div>
                                {post.jobCondition.map((condition,index)=>{
                                  return <span className='text-sm' key={index}>{condition}
                                   {
                                    index < post.jobCondition.length-1 && ' / '
                                }
                                   </span>
                                })}
                            </div>
                            <Badge bg="primary" pill>
                                14
                            </Badge>
                        </ListGroup.Item>
                    </ListGroup>
                );
            })}
        </div>
    );
}

export default SearchResult;
