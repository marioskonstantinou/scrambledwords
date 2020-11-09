# Scrambled Strings

### Prerequisites
1. `sbt` - Project was build against `1.4.2` but earlier versions can be used
1. `Java SDK` - `1.8` and above (Used `1.8.0_251`) 
1. `Docker` (Optional for running dockerized version)
---
### How to
#### Build and package project
1. In sbt shell run `assembly` : This compiles and packages the app as `.jar`
---
#### Cli Args
1. `Dictionary (--dictionary)`: Path to dictionary file
1. `Input (--input)`: Path to input file
---
#### Run project
1. Locate folder `data` in root of project that contain and edit the files
    1. A dictionary: `dictionary.txt`
    1. An input: `input.txt`

##### Running by: Project instructions
    After running `assembly` successfully locate `scrmabled-strings.sh` at the root of the project as:
  ```
  ./scrmabled-strings.sh --dictionary [PATH TO DICTIONARY FILE] --input [PATH TO INPUT FILE]
  ```
If ran successfully you should see an output close to the following: 
```
 Starting job with JAR ./target/scala-2.13/scrambledwords-1.0.0.jar
 2020-11-09 23:28:03,446 [INFO] from com.challenge.scrambled.model.Dictionary$ in main - Filtered dictionary Dictionary(List(axpaj, pxaj, nrbt, jxdn, bd))
 2020-11-09 23:28:03,449 [INFO] from com.challenge.scrambled.utils.DictionaryUtils$ in main - Validating total length for 5 words
 2020-11-09 23:28:03,449 [INFO] from com.challenge.scrambled.utils.DictionaryUtils$ in main - Total dictionary length 19 is within set threshold (105)
 2020-11-09 23:28:03,476 [INFO] from com.challenge.scrambled.Main$ in main - Case #1: 3
 2020-11-09 23:28:03,476 [INFO] from com.challenge.scrambled.Main$ in main - Case #2: 2
 2020-11-09 23:28:03,476 [INFO] from com.challenge.scrambled.Main$ in main - Case #3: 1
 Job finished with JAR ./target/scala-2.13/scrambledwords-1.0.0.jar built from BRANCH  and COMMIT_ID .
```

##### Running by: Sbt shell
```
run --dictionary [PATH TO DICTIONARY FILE] --input [PATH TO INPUT FILE]
```

###### Troubleshooting
1. `Jar file not found...Please follow README instructions how to build`: Please make sure you've ran `assembly` command correctly. 
To validate navigate to `target/target/scala-2.13` and make sure `scrambledwords-1.0.0.jar` is present
1. `zsh: permission denied: ./scrmabled-strings.sh`: Please run `chmod +x scrmabled-strings.sh` and rerun project

#### Running by: Docker (docker-compose.yml)
##### Publish
In sbt shell run:
1. `docker` - This builds the docker image and publishes locally the project image `com.challenge/scrambledwords:latest`

##### Run
Locate `docker-compose.yml` file and execute `docker-compose up`. You should expect to see an output similar to the following:
```
Creating volume "scrambledwords_data" with default driver
Creating scrambled ... done
Attaching to scrambled
scrambled    | Starting job with JAR /app/scrambledwords-1.0.0.jar
scrambled    | 2020-11-09 23:20:29,717 [INFO] from com.challenge.scrambled.model.Dictionary$ in main - Filtered dictionary Dictionary(List(axpaj, pxaj, nrbt, jxdn, bd))
scrambled    | 2020-11-09 23:20:29,723 [INFO] from com.challenge.scrambled.utils.DictionaryUtils$ in main - Validating total length for 5 words
scrambled    | 2020-11-09 23:20:29,723 [INFO] from com.challenge.scrambled.utils.DictionaryUtils$ in main - Total dictionary length 19 is within set threshold (105)
scrambled    | 2020-11-09 23:20:29,784 [INFO] from com.challenge.scrambled.Main$ in main - Case #1: 3
scrambled    | 2020-11-09 23:20:29,784 [INFO] from com.challenge.scrambled.Main$ in main - Case #2: 2
scrambled    | 2020-11-09 23:20:29,784 [INFO] from com.challenge.scrambled.Main$ in main - Case #3: 1
scrambled    | Job finished with JAR /app/scrambledwords-1.0.0.jar built from BRANCH  and COMMIT_ID .
scrambled exited with code 0
```

##### Notes:
1. `docker-compose.yml` creates and makes use of an image with *hardcoded* args (files found in `data` folder) 
1. Dockerfile is generated automatically when `docker` command is run and can be found under 
`target/docker/Dockerfile`

---

#### Running Tests
In sbt shell
```
clean
test
```
---
#### Configuration
The following configurations have been added based on limits of the exercise for flexibility found under `application.conf`
and also filtering in app logic

```
1. [exercise] Each word in the dictionary is between 2 and 105 letters long, inclusive.
    i. dictionary.min-length=2
    ii. dictionary.max-length=105
2. [exercise] The sum of lengths of all words in the dictionary does not exceed 105.
    i. dictionary.total-length=105
3. [other] Skips anagram of words in case length is less than provided
    anagram.min-length=3
```

#### API Documentation
1. In sbt shell run `doc`
1. Navigate to `/target/scala-2.13/api` and open `index.html`