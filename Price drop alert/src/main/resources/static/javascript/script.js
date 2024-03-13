const search = ()=> {
    console.log("searching...");
    let query=$("#search-input").val();
    console.log(query);
    if(query==''){
        $(".search-result").hide();
    }else {
        //search
        console.log(query);

        //sending request to server
        let url = `http://localhost:8080/admin/getproductbyname/${query}`;
        fetch(url).then((response) => {
            return response.json();
        })
            .then((data) => {
                //data.....
                console.log(data);
                let text = `<div class='list-group'>`
                data.forEach((productapi)=>{
                    text += `<a href='/user/contact' class='list-group-item list-group-item-info list-group-item-action'>${productapi.p_url}</a>`
                });
                text+=`</div>`;
                $(".search-result").html(text);
                $(".search-result .list-group-item").addClass("list-group-item");
                $(".search-result").show();
            });
        $(".search-result").show();
    }
}
