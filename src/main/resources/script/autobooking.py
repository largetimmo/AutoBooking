import sys
import io
import requests
import json

USER_AGENT = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36'
LOGIN_URL = 'https://booking.carleton.ca/Portal/Portal/Services/Login.php'
LOGIN_TEST = 'https://booking.carleton.ca/Portal/index.php?p=FindRoomSS&r=1'
LOGIN_PAGE = 'https://booking.carleton.ca/Portal/index.php'
FINDROOM_URL = 'https://booking.carleton.ca/Portal/Portal/Services/SelfServiceFindRoomList.php'
BOOKING_URL = 'https://booking.carleton.ca/Portal/index.php?p=ConfirmBooking'
CREATE_BOOKING_URL = 'https://booking.carleton.ca/Portal/Portal/Services/CreateBookingRequest.php'
search_data = {
    'cboRequestType': 'b3bbdbb9-9a28-4cb9-9ce4-ff38787be37b',
    'startDate': 1524441600,  # NEED REPLACED
    'isSelfService': 1,
    'startTime': 1080,  # NEED REPLACED
    'duration': 120,  # NEED REPLACED
    'endTime': 1200,  # NEED REPLACED
    'originalRequestID': '00000000-0000-0000-0000-000000000000'
}

booking_page_param = {
    'p': 'FindRoomSS',
    'r': 1
}

header = {
    'User-Agent': USER_AGENT,
    'Host;': 'booking.carleton.ca',
    'Origin': 'https://booking.carleton.ca',
    'Referer': 'https://booking.carleton.ca/Portal/index.php'
}

booking_confirm_data = {
    'selfService': 1,
    'txtRequestDisclaimer': 'Select OK to submit this request.',
    'txtRoomConfigId': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a',  # NEED REPLACED  ROOM ROW ID
    'txtOriginalRequestId': '00000000-0000-0000-0000-000000000000',
    'cboRequestType': 'b684b44a-00ee-43b1-aae1-567f979816df',  # NEED REPLACED, ROOM CALLBACK ID
    'txtNumberOfAttendees': 0,
    'dpStartDate_stamp': 1524441600,
    # NEED REPLACED TIME, (the difference between the target day and 1969.12.31) *86400
    'cboStartTime': 1080,
    # NEED REPLACED -> room start time,Starttime * 60 = this, e.g. starttime is 3p.m,so it's 15*60 = 900
    'cboDuration': 120,  # NEED REPLACED -> booking duration,default 120 is fine for 2 hours
    'txtNbOfPeople': 0,
    'txtMinArea': 0,
    'txtRoomId': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a',  # NEED REPLACED  ROOM ROW ID
    'cboRoomConfiguration': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a',  # NEED Replaced, ROOM ROW ID
    'btnConfirm': 'Confirm'
}


def Booking(username, password, datetime, starttime, roomnum, duration):
    session = requests.Session()  # create a session
    res_login_page = session.get(LOGIN_PAGE)  # go to login page -> get cookie
    data = {'txtUsername': username, 'txtPassword': password}  # create login data
    cookie = session.cookies  # get the cookie acquired from the login page
    res = requests.post(LOGIN_URL, data=data, cookies=cookie)  # send login request with cookie
    res_login_page = session.get(LOGIN_PAGE, cookies=cookie)  # goto login page again

    #replace data in search template
    search_data["startDate"] = datetime
    search_data["startTime"] = starttime
    search_data["duration"] = duration
    search_data["endTime"] = starttime+duration

    res = session.post(FINDROOM_URL, cookies=cookie, data=search_data)  # get room information
    json_str = res.text  # returned room list

    # remove html code
    json_bgein_index = json_str.find("data-listData=")
    json_str = json_str[json_bgein_index + len("data-listData=") + 1:]
    json_end_index = json_str.find("></div>")
    json_str = json_str[:json_end_index - 1]

    # parse to json
    json_obj = json.load(io.StringIO(json_str))
    # get content
    room_list = json_obj["RowData"]
    room_json = None
    room_row_id = None
    # find the room json object associated with the room number
    for item in room_list:
        if (item["rowData"][2] == roomnum):
            room_json = item  # the room object
            room_row_id = item["rowID"]  # room row id
            break

    if(room_json == None):
        print("Cannot find related room.Exiting.....")
        exit(-1)
    room_callback_id = room_json["callbackArgument"][0]

    # replace the data in request template
    booking_confirm_data["txtRoomConfigId"] = room_row_id
    booking_confirm_data["txtRoomId"] = room_row_id
    booking_confirm_data["cboRoomConfiguration"] = room_row_id
    booking_confirm_data["cboRequestType"] = room_callback_id
    booking_confirm_data["dpStartDate_stamp"] = datetime
    booking_confirm_data["cboStartTime"] = starttime
    booking_confirm_data["cboDuration"] = duration
    res = requests.post(CREATE_BOOKING_URL, data=booking_confirm_data, cookies=cookie)
    return res.status_code


def main(argv):
    # Argument format:
    # 1 -> username
    # 2 -> password
    # 3-> datetime(int)
    # 4 -> starttime(int)
    # 5 -> duration(int)
    # 6 -> roomnumber

    if (len(argv) != 6):
        exit(-1)
    username = argv[0]
    password = argv[1]
    datetime = int(argv[2])
    starttime = int(argv[3])
    duration = int(argv[4])
    roomnumber = argv[5]
    status = Booking(username=username, password=password, datetime=datetime, starttime=starttime, roomnum=roomnumber,
                     duration=duration)
    return (status)


if __name__ == '__main__':
    main(sys.argv[1:])

