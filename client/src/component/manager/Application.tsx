import { Fragment } from 'react';

import {
  BriefcaseIcon,
  CalendarIcon,
  CheckIcon,
  ChevronDownIcon,
  CurrencyDollarIcon,
  Bars3CenterLeftIcon,
  LinkIcon,
  MapPinIcon,
  PencilIcon,
} from '@heroicons/react/20/solid'
import { Menu, Transition } from '@headlessui/react';
import Editor from './Editor';
import { useState } from 'react';
import axios from 'axios';
import { ResponseInfo, UpdateInfo } from '../../common/types/propType';
import ApplicationDetail from './ApplcationDetail';
import { Button, Modal } from 'react-bootstrap';
import { useEffect } from 'react';


function classNames(...classes:any) {
  return classes.filter(Boolean).join(' ')
}

function Application({info}:ResponseInfo) {
  const [show, setShow] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [detail, setDetail] = useState(false);
  const [updateData, setUpdateData] = useState(info);

  const handleSave = () => {
    setShow(false);
  }
  const handleShow = () => setShow(true);

  const deleteApplication = async () => {
    const url = "/api/v1/job/"+info.id;
    try{
      await axios({
        method:"delete",
        url: url
      });
      setDeleteModal(false);
      window.location.reload();
    }catch(err){
      alert("알 수 없는 에러가 발생했습니다. 잠시 후에 다시 시도해주세요");
    }
  }

  return (
    <div className="lg:flex lg:items-center lg:justify-between">
      <div className='flex flex-col'>
        <div className="min-w-0 flex-1">
          <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">
            {info.companyName}
          </h2>
          <div className="mt-1 flex flex-col sm:mt-0 sm:flex-row sm:flex-wrap sm:space-x-6">
            <div className="mt-2 flex items-center text-sm text-gray-500">
              <BriefcaseIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-gray-400" aria-hidden="true" />
              {info.position}
            </div>
            <div className="mt-2 flex items-center text-sm text-gray-500">
              <MapPinIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-gray-400" aria-hidden="true" />
              {info.location}
            </div>
            <div className="mt-2 flex items-center text-sm text-gray-500">
              <CurrencyDollarIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-gray-400" aria-hidden="true" />
              {info.salary}만원
            </div>
            <div className="mt-2 flex items-center text-sm text-gray-500">
              <CalendarIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-gray-400" aria-hidden="true" />
              {info.applyDate}
            </div>
          </div>
        </div>
        <div className="mt-5 flex lg:mt-0 lg:ml-4">
          <span className="hidden sm:block">
            <button
              type="button"
              className="inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              onClick={handleShow}
            >
              <PencilIcon className="-ml-1 mr-2 h-5 w-5 text-gray-500" aria-hidden="true" />
              수정
            </button>
          </span>
          <span className="ml-3 hidden sm:block">
            <a href={info.link} className="no-underline" target="_blank">
              <button
                type="button"
                className="inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                <LinkIcon className="-ml-1 mr-2 h-5 w-5 text-gray-500" aria-hidden="true"/>
                지원 링크
              </button>
            </a>
          </span>
          <span className="sm:ml-3">
            <button
              type="button"
              className="inline-flex items-center rounded-md border border-transparent bg-stone-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              onClick={()=>setDetail(!detail)}
            >
              <Bars3CenterLeftIcon className="-ml-1 mr-2 h-5 w-5" aria-hidden="true" />
              자세히보기
            </button>
          </span>
          <span className="sm:ml-3">
            <button
              type="button"
              className="inline-flex items-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              onClick={()=>setDeleteModal(true)}
            >
              <CheckIcon className="-ml-1 mr-2 h-5 w-5" aria-hidden="true" />
              지원종료
            </button>
          </span>
          <Modal show={deleteModal} onHide={()=>setDeleteModal(false)}>
                    <Modal.Header closeButton>
            <Modal.Title>지원 삭제</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
            <p>지원내역을 삭제하시겠습니까?</p>
                    </Modal.Body>
                    <Modal.Footer>
            <Button variant="secondary" onClick={()=>setDeleteModal(false)}>닫기</Button>
            <Button variant="primary" onClick={deleteApplication}>삭제</Button>
                    </Modal.Footer>
          </Modal>
          
          {/* Dropdown */}
          <Menu as="div" className="relative ml-3 sm:hidden">
            <Menu.Button className="inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
              More
              <ChevronDownIcon className="-mr-1 ml-2 h-5 w-5 text-gray-500" aria-hidden="true" />
            </Menu.Button>
            <Transition
              as={Fragment}
              enter="transition ease-out duration-200"
              enterFrom="transform opacity-0 scale-95"
              enterTo="transform opacity-100 scale-100"
              leave="transition ease-in duration-75"
              leaveFrom="transform opacity-100 scale-100"
              leaveTo="transform opacity-0 scale-95"
            >
              <Menu.Items className="absolute right-0 z-10 mt-2 -mr-1 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <Menu.Item>
                  {({ active }) => (
                    <a
                      href="#"
                      className={classNames(active ? 'bg-gray-100 no-underline' : '', 'block px-4 py-2 text-sm text-gray-700 no-underline')}
                    >
                      수정
                    </a>
                  )}
                </Menu.Item>
                <Menu.Item>
                {({ active }) => (
                    <a
                      href={info.link}
                      className={classNames(active ? 'bg-gray-100 no-underline' : '', 'block px-4 py-2 text-sm text-gray-700 no-underline')}
                      target="_blank"
                    >
                      지원 링크
                    </a>
                  )}
                </Menu.Item>
              </Menu.Items>
            </Transition>
          </Menu>
        </div>
        <Editor info={updateData} setInfo={setUpdateData} show={show} setShow={setShow} onHide={handleSave}/>
        {
          detail&&<ApplicationDetail info={info}/>
        }
      </div>
    </div>
  )
}

export default Application;