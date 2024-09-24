import './App.css';
import axios from 'axios';
import { useEffect, useState } from 'react';

function App() {
  //let [img, setImg] = useState('');
  let [imgs, setImgs] = useState([]);
  const host = "http://localhost:8080"; //axios 설치 필요

  useEffect (() => {
      axios.post(host + "/all")
          .then(res => {
              console.log(res,res.data);
              let viewUrl = host + "/view?url="
              // let imgUrl = res.data.url;
              // setImg(viewUrl + imgUrl);
              let list = []; //처음 빈화면에서 최초초초초 넣을 때
              for(let i = 0; i < res.data.length; i++ ) {
                list[i] = viewUrl + res.data[i].path;

              }
              setImgs(list);
            })
          .catch(error => console.log(error));
  },[]);

  const submitEvent = e => {
        e.preventDefault();
        // console.log(e.target.file.files[0]);

        const formData = new FormData(); //새로운 객체 생성
        for(let i = 0; i < e.target.file.files.length; i++) {
          formData.append("file",e.target.file.files[i]); // key , value 의 형태로 담기(Json)
        }

        axios.post(host+"/filesUpload",formData)
              .then(res => {
              // console.log(res,res.data);
              if(res.data.state) {
                let viewUrl = host + "/view?url="
                // let imgUrl = res.data.url;
                // setImg(viewUrl + imgUrl);
                let list = imgs;
                res.data.list.forEach(url => {
                  list = [...list, viewUrl + url];
                });
                setImgs(list);
              } else {
                alert('연결 실패');
              }
              })
              .catch(error => console.log(error));
        };
  return (
   <>
     <header>
        <h1 className="text-center">이미지 게시판</h1>
    </header>
    <main>
        <form onSubmit={submitEvent}>
            <div className="input-body">
                <label htmlFor="title">그림</label>
                <input type="file" name="file" accept="image/*" autoComplete="off" multiple />
            </div>
            <div className="input-body">
                <input type="submit" value="전송" />
            </div>
        </form>
        <div className="imgs">
             {/* {
              img === '' ?
                <></>
                :
                <img src={img} className="img" />
            } */}
            
            {
              imgs.map((row, index) => {
                return (
                  <a href = {row} key={index} >
                    < img src = {row} className = "img" key = {index} />
                  </a>
                );
              })
            }
        </div>
    </main>
   </>
  );
}

export default App;