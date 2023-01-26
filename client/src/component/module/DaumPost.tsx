import React from 'react';
import { useDaumPostcodePopup } from 'react-daum-postcode';


const DaumPost = () => {
    const CURRENT_URL =
		'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  const open = useDaumPostcodePopup(CURRENT_URL);

  const handleComplete = (data:any) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    console.log(fullAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
  };

  const handleClick = () => {
    open({ onComplete: handleComplete });
  };

  return (
    <button type='button' onClick={handleClick}>
      Open
    </button>
  );

//   import React from 'react';
// import { useDaumPostcodePopup } from 'react-daum-postcode';

// const Postcode = () => {
//   const open = useDaumPostcodePopup(scriptUrl);

//   const handleComplete = (data) => {
//     let fullAddress = data.address;
//     let extraAddress = '';

//     if (data.addressType === 'R') {
//       if (data.bname !== '') {
//         extraAddress += data.bname;
//       }
//       if (data.buildingName !== '') {
//         extraAddress += extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
//       }
//       fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
//     }

//     console.log(fullAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
//   };

//   const handleClick = () => {
//     open({ onComplete: handleComplete });
//   };

//   return (
//     <button type='button' onClick={handleClick}>
//       Open
//     </button>
//   );
// };
};

export default DaumPost;