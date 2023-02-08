import { ListGroup } from "react-bootstrap";
import { Link } from "react-router-dom";
import { SearchResultData } from "../../common/types/propType";

interface PropsInfo {
    searchResult: SearchResultData;
}

function SearchResult(props: PropsInfo) {
    return (
        <div>
            {props.searchResult.map((post, i) => {
                return (
                    <ListGroup as="ol" key={i} className='my-3'>
                        <ListGroup.Item
                            as="li"
                            className="d-flex justify-content-between align-items-start"
                        >
                            <div className="ms-2 me-auto">
                                <div className="fw-bold">
                                    <Link
                                        to={"//" + post.url}
                                        className="no-underline text-black"
                                        target="_blank"
                                    >
                                        {post.title}
                                    </Link>
                                </div>
                                {post.jobCondition.map((condition, index) => {
                                    return (
                                        <span className="text-sm" key={index}>
                                            {condition}
                                            {index <
                                                post.jobCondition.length - 1 &&
                                                " / "}
                                        </span>
                                    );
                                })}
                            </div>
                            <Link to={"//" + post.companyUrl} target="_blank" className='no-underline'>
                                <div className='py-2 px-3 '>
                                    {post.companyName}
                                </div>
                            </Link>
                        </ListGroup.Item>
                    </ListGroup>
                );
            })}
        </div>
    );
}

export default SearchResult;
