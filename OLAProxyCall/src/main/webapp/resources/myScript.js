var cabminidetails=[],cabsedandetails=[], booking_done=false, userLat, userLng, mini=0,sedan=0;

$(document).ready(function(){
	$('#sedan').on('click',function(){
		$('.option2').addClass('slideanim');
		$('#minidetail').removeClass('active');
		openpopup(cabsedandetails,"sedan");
	});

	$('#mini').on('click',function(){
		$('#minidetail').addClass('active');
		$('.option2').removeClass('slideanim');
		openpopup(cabminidetails,"mini");
	});
	$('#userAddress').change(function(){
		getLocation();
	});
	$('.ridenow button').on('click',function(){
		$('#scrim').addClass('hide');
		getCabsInfo();
	});
	$('#checkstatus').on('click',function(){
		if(booking_done){
			$('.appcontainer').addClass('hide');
			populate_trackMap();
		//Add function for tracking multiple cabs
		}else{
			alert('No recent booking');
		}
	});
	$('#track').on('click',function(){
		if($('.sidepanel').hasClass('slider')){
			$('.sidepanel').removeClass('slider');
			$('#cab-selection').removeClass('hide');
			$('#ride-option').removeClass('hide');
		}else{
			$('.sidepanel').addClass('slider');
			$('#cab-selection').addClass('hide');
			$('#ride-option').addClass('hide');
		}
	});
})

function getCabsInfo(){
	mini= $('.option1 .caboption input:checked').length;
	sedan= $('.option2 .caboption input:checked').length;
	alert("Mini: "+ mini + " Sedan: "+ sedan);
	$.ajax({
		url:'http://localhost:7070/OLAProxyCall/rest/api/bookRides?lat='+userLat+'&lng='+userLng+'&mode=NOW&countMini='+mini+'&countsedan='+sedan
	}).done(rideBookCallback);
}

function rideBookCallback(data){
	booking_done=true;
	alert(JSON.stringify(data));
}

function openpopup(cabdetails,info){
	$('#scrim').removeClass('hide');
	var elem;
	if(info==="mini")
		elem=$('.option1 .caboption');
	else
		elem=$('.option2 .caboption');
	if(elem.children().length==0){
		for(var i=0;i<cabdetails.length;i++){
			elem.append('<input type="checkbox" class="cb"><div class="detail"><span style="float:left">Cab '+(i+1)+'</span><span style="float:right;">'+cabdetails[i].eta+' min </span</div>');
		}
	}
	$(".cb").change(function() {
	    if(this.checked) {
	        $('.ridenow button').removeAttr('disabled');
	    }
	    if($('input:checked').length==0){
			$('#scrim .caboption').find('button').remove();
			$('.ridenow').find('button').attr('disabled', true);
		}
	});
	if(info=="mini" && cabdetails[0].fare_breakup){
		$('#scrim .option1 .base span').text(cabdetails[0].fare_breakup[0].base_fare);
		$('#scrim .option1 .abovebase span').text(cabdetails[0].fare_breakup[0].cost_per_distance);
		$('#scrim .option1 .ridetimerate span').text(cabdetails[0].fare_breakup[0].ride_cost_per_minute);
	}
	else if(info=="sedan" && cabdetails[0].fare_breakup){
		$('#scrim .option2 .base span').text(cabdetails[0].fare_breakup[0].base_fare);
		$('#scrim .option2 .abovebase span').text(cabdetails[0].fare_breakup[0].cost_per_distance);
		$('#scrim .option2 .ridetimerate span').text(cabdetails[0].fare_breakup[0].ride_cost_per_minute);
	}
	
}

function getCurrentLocation() {
	$('#userAddress').val('');
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
        alert("Geolocation is not supported by this browser.");
    }
}
function showPosition(position) {
		userLat=position.coords.latitude;
		userLng=position.coords.longitude;
	    initialize(userLat,userLng);
	    getRideAvailability(position.coords.latitude,position.coords.longitude);
}

function getLocation(){
	var address = $("#userAddress").val();
	$.ajax({
		type:"GET",
		accept:"text/json",
		url:"https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyCJs8rbnYSod1KxjMYJBiYsWgFrgf3PbVo",
		success:function(result){
			showOnMap(result);
		},
		error:"",
	});
}

function showOnMap(data){
	if(data.status == "OK")
	{
		var longitude = data.results[0].geometry.location.lng;
		var lattitude = data.results[0].geometry.location.lat;
	    initialize(lattitude,longitude);
	    getRideAvailability(lattitude,longitude);
	}else{
		alert("location not found");
	}
}

function initialize(lattitude,longitude) {
			var myCenter=new google.maps.LatLng(lattitude,longitude);
			var mapCanvas = document.getElementById('navigation-container');
	        var mapOptions = {
	          center: new google.maps.LatLng(lattitude, longitude),
	          zoom: 15,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        }
	        var map = new google.maps.Map(mapCanvas, mapOptions);
	        var marker=new google.maps.Marker({
	        	  position:myCenter,
	        	  });
	        marker.setMap(map);
}

function getRideAvailability(lat,lng){
	$.ajax({
		url:'http://localhost:7070/OLAProxyCall/rest/api/Rides?lat='+lat+'&lng='+lng 
	}).done(rideAvailCallback);
}

function rideAvailCallback(data){
	getAllCabsAvailability(data);
}

function getAllCabsAvailability(data){
	for(var i=0,j=0;i<data.categories.length && j<data.categories.length;){
		if(data.categories[i].id==="mini"){
			if(!cabminidetails[i]){
				cabminidetails[i]={};
			}
			cabminidetails[i].id=data.categories[i].id;
			cabminidetails[i].eta=data.categories[i].eta;
			cabminidetails[i].time_unit=data.categories[i].time_unit;
			cabminidetails[i].fare_breakup=data.categories[i].fare_breakup;
			i++;
		}else if(data.categories[i].id==="sedan"){
			if(!cabsedandetails[j]){
				cabsedandetails[j]={};
			}
			cabsedandetails[j].id=data.categories[i].id;
			cabsedandetails[j].eta=data.categories[i].eta;
			cabsedandetails[j].time_unit=data.categories[i].time_unit;
			cabsedandetails[j].fare_breakup=data.categories[i].fare_breakup;
			i++;
			j++;
		}
	}
	//Sort all available mini cabs by ETA
	cabminidetails.sort(function(a,b){
		return a.eta -b.eta;
	});
	//Sort all available mini cabs by ETA
	cabsedandetails.sort(function(a,b){
		return a.eta -b.eta;
	});

	//
	$('#minidetail .eta').text(cabminidetails[0].eta + ' min');
	$('#minidetail .num').text(objectSize(cabminidetails) + ' cabs');
	$('#sedandetail .eta').text(cabsedandetails[0].eta + ' min');
	$('#sedandetail .num').text(objectSize(cabsedandetails)+ ' cabs');
}

function objectSize(the_object) {
  /* function to validate the existence of each key in the object to get the number of valid keys. */
  var object_size = 0;
  for (key in the_object){
    if (the_object.hasOwnProperty(key)) {
      object_size++;
    }
  }
  return object_size;
}


var markerLocation = [
	['Current_loc', 12.950536999999999, 77.64210109999999, 1],
	['MurugeshPalya', 12.9562593, 77.6534629, 2],
	['HAL', 12.9705598, 77.650033, 3]
];

function populate_trackMap() {
			var mapCanvas = document.getElementById('trackmap');
	        var mapOptions = {
	          center: new google.maps.LatLng(markerLocation[0][1],markerLocation[0][2]),
	          zoom: 10,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        }
	        var map = new google.maps.Map(mapCanvas, mapOptions);
	        for(var i=0;i<markerLocation.length;i++){
	        	myCenter=new google.maps.LatLng(markerLocation[i][1],markerLocation[i][2]);
		        var marker=new google.maps.Marker({
		        	  position:myCenter,
		        	  });
		        marker.setMap(map);
	        }
	       // getRideAvailability(lattitude,longitude);
}






