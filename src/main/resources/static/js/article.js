let deleteBtn = document.querySelector("#delete-btn");
let modifyBtn = document.querySelector("#modify-btn");

let goModifyBtn = document.querySelector("#goModify-btn");
let createBtn = document.querySelector("#create-btn");
let goEnrollBtn = document.querySelector("#goEnroll-btn");

let articleId = document.querySelector("#article-id").value;

if (!!deleteBtn) {
    console.log('!!deleteBtn : ', !!deleteBtn);
    deleteBtn.addEventListener('click', () => {
        deleteArticle(articleId);
    });
}

if (!!modifyBtn) {
    console.log('modifyBtn is running..');
    modifyBtn.addEventListener('click', () => {
        location.replace(`/new-article?id=${articleId}`);
    });
}

if (!!goEnrollBtn) {
    goEnrollBtn.addEventListener('click', () => goEnrollAndModify());
}

if (!!goModifyBtn) {
    goModifyBtn.addEventListener('click', () => goEnrollAndModify());
}

if (!!createBtn) {
    createBtn.addEventListener('click', () => {
        location.replace('/new-article');
    })
}

function goEnrollAndModify() {
    let title = document.querySelector("#title").value;
    let content = document.getElementById("content").value;
    let params = new URLSearchParams(location.search);
    let id = params.get('id');
    let url = !!id ? '/api/articles/' + id : '/api/articles'
    console.log('title : ', title);
    console.log('content : ', content);

    fetch(url, {
        method: !!id ? 'PUT' : 'POST',
        headers: {
            'Content-Type': "application/json",
        },
        body: JSON.stringify({
            title: title,
            content: content
        })
    })
        .then(res => res.json())
        .then(result => console.log(result));
}

function deleteArticle(artId) {
    let url = `/api/articles/${artId}`;
    fetch(url, {method: 'delete'})
    .then(res => res.json())
    .then(result => location.replace(result.replaceUrl));
}