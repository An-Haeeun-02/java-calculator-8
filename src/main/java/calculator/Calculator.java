package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    // 기본 구분자 지정
    private static final String DELIMITER = "[,:]";
    //커스텀 구분자 패턴
    private static final Pattern CUSTOM_PATTERN_DELIMITER = Pattern.compile("//(.)\\\\n(.*)");
    //커스텀 구분자 지정
    private static String CUSTOM_DELIMITER ="@";

    /**
     * 기본적인 입력 검증을 수행 마지막 문자가 숫자가 아니거나 구분자로 시작하는 경우 예외를 발생
     *
     * @param input 입력된 문자열
     * @return 검증된 입력 문자열
     * @throws IllegalArgumentException 입력이 잘못된 경우
     */

    //1차 문자열 검증 - 앞뒤 문자가 아닌 경우 검사
    private static String defaultCheck(String input) throws IllegalArgumentException {

        //마지막 문자가 숫자가 아닌 경우 오류 발생
        char lastChar = input.charAt(input.length() - 1);
        if (!Character.isDigit(lastChar)) { //숫자가 아니라면
            throw new IllegalArgumentException("마지막 입력이 숫자가 아닙니다.");
        }
        // 커스텀 구분자 패턴 점검 (input이 "//"로 시작하는 경우)
        if (input.startsWith("//")) {
            return customCheck(input);
        }
        //첫번째 문자가 숫자가 아닌경우
        char firstChar = input.charAt(0);
        if (firstChar == ',' || firstChar == ':') { //,나 ; 라면
            throw new IllegalArgumentException("입력이 구분자로 시작합니다.");
        }
        //입력값 반환
        return input;
    }

    /**
     * 커스텀 구분자가 있는지 확인하고, 해당 구분자로 문자열을 처리
     *
     * @param input 입력된 문자열
     * @return 커스텀 구분자를 처리한 문자열
     * @throws IllegalArgumentException 커스텀 패턴에 오류가 있는 경우
     */

    //커스텀 구분자 여부 점검
    private static String customCheck(String input) throws IllegalArgumentException {
        // 커스텀 문자열 패턴
        Matcher matcher = CUSTOM_PATTERN_DELIMITER.matcher(input);

        // 커스텀 패턴 일치 여부 확인
        if (matcher.matches()) { // 커스텀 패턴과 일치하면
            // 지정된 커스텀 문자열 추출
            String regex = matcher.group(1); // 커스텀 구분자
            String modifiedInput = matcher.group(2); // 커스텀 구분자 이후의 문자열

            //커스텀 문자열 뒤 확인
            if (modifiedInput.isEmpty()) {
                throw new IllegalArgumentException("");
            }

            // 커스텀 문자열을 CUSTOM_DELIMITER(기본: @)로 치환
            return modifiedInput.replaceAll(Pattern.quote(regex), CUSTOM_DELIMITER);
        } else {
            throw new IllegalArgumentException("커스텀 패턴에 오류가 있습니다.");
        }
    }

    /**
     * 입력된 문자열에 대해 구분자 및 허용되지 않은 문자를 검사
     *
     * @param input 입력된 문자열
     * @return 검증된 문자열
     * @throws IllegalArgumentException 입력이 잘못된 경우
     */
    //문자열에 대한 검증 메소드
    private static String preciseCheck(String input) throws IllegalArgumentException {
        //문자열 1차 검증 및 커스텀 검증
        String inputResult = defaultCheck(input);

        //구분자와 숫자 이외의 것 허용 된지 검증 패턴
        String allowedCharsPattern = "[0-9" + DELIMITER + Pattern.quote(CUSTOM_DELIMITER) + "-]*";

        if (!inputResult.matches("^" + allowedCharsPattern + "$")) {
            throw new IllegalArgumentException("허용 되지 않은 문자가 포함되어 있습니다. " + inputResult);
        }

        //구분자 중복 사용 검증
        String delimitersForOverlapCheck = DELIMITER.substring(1, DELIMITER.length() - 1) + Pattern.quote(CUSTOM_DELIMITER);
        String unallowedOverlapPattern = "[" + delimitersForOverlapCheck + "]{2,}";

        if (Pattern.compile(unallowedOverlapPattern).matcher(inputResult).find()) {
            throw new IllegalArgumentException("구분자가 중복으로 사용되고 있습니다. "+inputResult);
        }

        return inputResult;
    }

    /**
     * 입력된 문자열을 구분자를 기준으로 분리하여 숫자를 추출
     *
     * @param input 입력된 문자열
     * @return 분리된 숫자 배열
     * @throws IllegalArgumentException 입력이 잘못된 경우
     */

    //구분자를 기준으로 숫자 분리
    private static  String[] numberExtraction(String input) throws IllegalArgumentException {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        //문자열에 대한 검증 진행
        String inputResult = preciseCheck(input);

        // 구분자를 기준으로 숫자 분리
        String allDelimitersRegex = "[" + DELIMITER.substring(1, DELIMITER.length() - 1) + Pattern.quote(CUSTOM_DELIMITER) + "]";

        return inputResult.split(allDelimitersRegex);
    }

    /**
     * 입력된 문자열에 대해 덧셈 수행
     * 음수는 허용되지 않으며 입력값에 음수가 포함된 경우 예외 발생
     *
     * @param input 덧셈할 숫자들의 문자열
     * @return 덧셈 결과
     * @throws IllegalArgumentException 음수가 포함된 경우 예외 발생
     */
    //문자열 덧셈
    static int sum(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        //구분자 분리
        String[] numbersList = numberExtraction(input);

        int sum = 0;
        for (String number : numbersList) {
            if (number.isEmpty()) {
                continue;
            }

            int numValue = Integer.parseInt(number);

            if (numValue < 0) {
                // 음수가 하나 이상 있을 경우 해당 음수 값들을 모두 보여줍니다.
                throw new IllegalArgumentException("음수(" + numValue + ")는 입력할 수 없습니다.");
            }

            sum += numValue;
        }
        return sum;
    }

}
