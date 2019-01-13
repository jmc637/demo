# demo CRUD api for appointments
Assuming you have docker on your machine you can test this application by doing the following
    - Run docker-compose up from command line
    - Get port by running docker ps
    - Test service on localhost by using found port
    
EndPoints

GET /appointments \
    - Gets a list of all appointments. \
    - Query params dateTimeStart and dateTimeEnd can be used to filter appointments within that date
range. If these query params are added will also be sorted by price asc. dateTimeStart and dateTimeEnd should be DateTimes in iso-8601 format. Ex. 2019-12-12T00:00:00

POST /appointments \
    - To create an appointment \
    - Ex. JSON appoinment to be sent in body \
{
	"appointmentStartDateTime": "2019-12-12T00:00:00",
	"appointmentEndDateTime": "2019-12-13T00:00:00",
	"status": false,
	"price": 20.0,
	"nameOfDoctor": "Julius"
}

PATCH /appointments/{APPOINTMENT_ID_HERE} \
    - To update an appoinment \
    - JSON should just be the fields you want changed similar to post format \
    - Fields that are not included will remain the same and not be changed

DELETE /appointments/{APPOINTMENT_ID_HERE} \
    - Deletes an appoinment
