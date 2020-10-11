/*es5*/


function boardDetailOne(id,no){//하나의 보스정보 가져오기 id-> pk, no-> category
console.log(no)
	var category= 0; 
	if(no == '스터디구인'){
		category = 2;
	} else if(no == '일반'){
		category = 1;
	}


	location.href = "/devst/board/detail/category?id="+id+"&no="+category;
}







/*jQuery*/
$(function(){
	$('#user_info').click(function(){
		$('#certified').load("/certified")
	})
	
	
	
	/*
	$('.mainItems').click(function(){
		var category = $(this).children('div')[0].className;
		if(category == 'categoryColorGreen'){
			location.href = '';
		} else if(category == 'categoryColorBlue'){
			location.href = '';
		}
		
	})*/
	
	
	
	$('#logo').click(function(){
		location.href="/";
	});
	
	
	$(".navItems").eq(0).addClass('active');
	
	
	
	
	$(document).on('click', '.navItems', function(){
		var category = $(this).text();
		var idx = $(this).index();
				$('.navItems').removeClass('active');
				$(".navItems").eq(idx).addClass('active');
		//alert(category)
		 	var header = $("meta[name='_csrf_header']").attr("content");
        	var token = $("meta[name='_csrf']").attr("content");
		$.ajax({
			url:'/devst?category='+category,
			type:'post',
			dataType:'json',
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);	// 헤드의 csrf meta태그를 읽어 CSRF 토큰 함께 전송
			},
			
			success:function(data){
				
				
				
				var categoryColorBlue = '<div class="categoryColorBlue"></div>';
				//console.log(data.jsonData)
				$('.mainItems').remove();
				for(var i=0; i<data.jsonData.length;i++){
					
					//brd_category 일반 -> 파랑  rgba(102,141,246,0.7); className categoryColorBlue
					//brd_category 스터디구인 -> 초록 rgba(5,195,157,0.7); className categoryColorGreen
					//<div class="mainItems" onclick="boardDetailOne(${list.brd_id}, '${list.brd_category }')">
					var items = '<div class="mainItems" onclick="boardDetailOne('+data.jsonData[i].brd_id+', \''+data.jsonData[i].brd_category+'\')">'
								+'<div class="categoryColor"></div>'
								+'<h3 class="mainItemsTitle">'+data.jsonData[i].brd_title+'</h3>'
								+'<div class="mainItemsMid">'
								+'<div class="mainItemsMidLeft">'
									+'<div class="mainItemsMidLeftTop">'
										+'<div class="mainItemsGood"><i class="far fa-thumbs-up"></i><p class="goodCnt">'+data.jsonData[i].brd_like_count+'</p></div>'
									+'</div>'
									+'<div class="mainItemsMidLeftBot">'
										+'<div class="mainItemsComment"><i class="far fa-comment"></i><p class="comment">10</p></div>'
										+'<div class="mainItemsBad"><i class="fas fa-eye"></i><p class="viewCnt">'+data.jsonData[i].brd_view_count+'</p></div>'
									+'</div>'
								+'</div>'
								+'<div class="mainItmesMidRight">'
									+'<div class="mainItmesNm"><i class="fas fa-user-circle"></i><p>'+data.jsonData[i].mem_nickname+'</p></div>'
									+'<div class="mainItmesDate"><p>'+data.jsonData[i].brd_update_date+'</p></div>'
								+'</div>'
							+'</div>'
								+'<div class="mainItemsDescWrap">'
								+'<p class="mainItemsDesc">'
									+data.jsonData[i].brd_content
								+'</p>'
							+'</div>'
							+'</div>';
				
					$('.mainWrap').append(items);
					if(data.jsonData[i].brd_category =="일반"){
						$(".mainItems  .categoryColor").eq(i).addClass("categoryColorBlue");
					} else if(data.jsonData[i].brd_category == "스터디구인"){
						$(".mainItems  .categoryColor").eq(i).addClass("categoryColorGreen");
					}
				}
				
				
				
				
				
				
			},
			error:function(data){
				console.log('실패')
			}
		})
		
		
	})
	
	
})