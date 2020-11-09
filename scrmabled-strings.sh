#!/usr/bin/env bash

if [ -z "$BUILD_JAR_NAME" ]
then
      BUILD_JAR_NAME="$(find ./*/*scala* -xdev -name scrambledwords-1.0.0.jar)"
fi

if [ -z "$BUILD_JAR_NAME" ]
then
    echo "Jar file not found...Please follow README instructions how to build"
    exit -1
fi

echo "Starting job with JAR $BUILD_JAR_NAME"

arguments_list() {
    echo "Example:
          $0
            --dictionary            <path>
            --input                 <path>
            "
}

if [ $# -lt 2 ]
then
    echo "Arguments not found..."
    arguments_list
    exit -1
else

    while [ $# -gt 0 ]
    do
    key="$1"

    case $key in
        --dictionary)
            DICTIONARY_PATH="$2"
            shift
        ;;
        --input)
            INPUT_PATH="$2"
            shift
        ;;
        *)
          echo "Unknown argument: Key: $key - Value: $2"
          shift
        ;;
    esac
    shift
    done

    java -jar "${BUILD_JAR_NAME}" \
    --dictionary "${DICTIONARY_PATH}" \
    --input "${INPUT_PATH}" \

    APP_RUN_STATUS="${?}"

    echo "Job finished with JAR ${BUILD_JAR_NAME} built from BRANCH $GIT_BRANCH and COMMIT_ID $GIT_COMMIT_ID."

    exit ${APP_RUN_STATUS}
fi
