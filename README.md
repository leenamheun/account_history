실행방법 :0 ) 

1. 데이터 세팅
RecodeHandlerTest의 하위 테스트들을 순차적으로 실행 시켜주세요!
( com.example.demo.record.RecodeHandlerTest ) 

  	1-1) initOffice() 실행

	  1-2) initBankAccount() 실행

	  1-3) initHistory() 실행

2. 서버구동
DemoApplication을 구동합니다. 

3. api확인

		3-1) 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.(단, 취소여부가 ‘Y’ 거래는 취소된 거래임, 합계 금액은 거래금액에서 수수료를 차감한 금액임)
		GET localhost:8080/api/v1/bankaccount/history/maxBy

		3-2) 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.(취소여부가 ‘Y’ 거래는 취소된 거래임)
		GET localhost:8080/api/v1/bankaccount/history/not

		3-3) 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.( 취소여부가 ‘Y’ 거래는 취소된 거래임)
		GET localhost:8080/api/v1/office/maxBy

		3-4) RecodeHandlerTest의 transferOffice() Test를 실행시켜 "분당점"을 폐업시켜주세요. 
		
		3-5) 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발( 취소여부가 ‘Y’ 거래는 취소된 거래임,)
		GET localhost:8080/api/v1/office/sumBy?name=분당점


