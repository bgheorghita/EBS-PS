# EBS-SPG

Tipul de paralelizare: thread-uri
  
Factorul de paralelism: se poate folosi maxim 1 thread / CPU Core. Daca programul ruleaza pe un sistem cu CPU ce contine 4 core-uri atunci factorul de paralelism poate sa fie maxim 4 (thread-uri)

  
##  Evaluare
  ### Pentru generarearea mesajelor s-a folosit un sistem cu CPU 8 core-uri (logical cores: 16), 2.3 GHz.
  
| Publicatii generate  | Factor de paralelizare (threads) | Timp executie (s) |
| :---                 |     :---:                        |          ---:      |
| 10.000               | - (main thread)                  |${\color{orange}0.088 - 0.096}$|
| 10.000               | 2                                |${\color{green}0.088 - 0.094}$|
| 10.000               | 3                                |${\color{green}0.086 - 0.091}$|
| 10.000               | 4                                |${\color{green}0.087 - 0.090}$|
| 10.000               | 5                                |${\color{green}0.084 - 0.087}$|
| 10.000               | 6                                |${\color{green}\textbf{0.082 - 0.085}}$|
| 100.000              | - (main thread)                  |${\color{orange}0.292 - 0.307}$|
| 100.000              | 2                                |${\color{green}0.288 - 0.304}$|
| 100.000              | 3                                |${\color{green}0.280 - 0.292}$|
| 100.000              | 4                                |${\color{green}\textbf{0.274 - 0.296}}$|
| 100.000              | 5                                |${\color{green}0.281 - 0.298}$|
| 100.000              | 6                                |${\color{green}0.278 - 0.283}$|
| 1.000.000            | - (main thread)                  |${\color{orange}1.538 - 1.553}$|
| 1.000.000            | 2                                |${\color{green}1.123 - 1.157}$|
| 1.000.000            | 3                                |${\color{green}0.949 - 0.993}$|
| 1.000.000            | 4                                |${\color{green}0.918 - 0.925}$|
| 1.000.000            | 5                                |${\color{green}0.896 - 0.904}$|
| 1.000.000            | 6                                |${\color{green}\textbf{0.877 - 0.892}}$|
| 3.000.000            | - (main thread)                  |${\color{orange}4.108 - 4.119}$|
| 3.000.000            | 2                                |${\color{green}2.777 - 2.843}$|
| 3.000.000            | 3                                |${\color{green}2.300 - 2.322}$|
| 3.000.000            | 4                                |${\color{green}2.084 - 2.109}$|
| 3.000.000            | 5                                |${\color{green}2.017 - 2.058}$|
| 3.000.000            | 6                                |${\color{green}\textbf{1.950 - 1.962}}$|
| 6.000.000            | - (main thread)                  |${\color{orange}8.302 - 8.321}$|
| 6.000.000            | 2                                |${\color{green}5.508 - 5.536}$|
| 6.000.000            | 3                                |${\color{green}4.419 - 4.473}$|
| 6.000.000            | 4                                |${\color{green}3.937 - 4.028}$|
| 6.000.000            | 5                                |${\color{green}3.743 - 3.795}$|
| 6.000.000            | 6                                |${\color{green}\textbf{3.668 - 3.891}}$|


| Subscriptii generate  |City Freq|TempFreq|WindFreq|MinFreqEqualOperatorForCityField| Factor de paralelizare | Timp executie (s) |
| :---                  |:---:    |:---:    |:---:  |:---:                           |     :---:              |          ---:      |   
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | - (main thread)        |${\color{orange}0.285 - 0.298}$|
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 2                      |${\color{red}0.390 - 0.393}$|
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 3                      |${\color{red}\textbf{0.458 - 0.477}}$|
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 4                      |${\color{red}0.376 - 0.474}$|
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 5                      |${\color{red}0.434 - 0.448}$|
|1.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 6                      |${\color{red}0.359 - 0.464}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | -                      |${\color{orange}0.983 - 1.024}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 2                      |${\color{green}0.886 - 0.934}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 3                      |${\color{green}0.845 - 0.876}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 4                      |${\color{green}0.860 - 0.906}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 5                      |${\color{green}\textbf{0.842 - 0.844}}$|
|5.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 6                      |${\color{green}0.830 - 0.897}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | -                      |${\color{orange}1.428 - 1.458}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 2                      |${\color{green}1.258 - 1.334}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 3                      |${\color{green}1.264 - 1.272}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 4                      |${\color{green}1.249 - 1.293}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 5                      |${\color{green}\textbf{1.220 - 1.237}}$|
|8.000.000              | 0.5     | 0.2     | 0.3   | 0.5                            | 6                      |${\color{green}1.224 - 1.274}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | -                      |${\color{orange}2.750 - 2.801}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | 2                      |${\color{green}2.305 - 2.325}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | 3                      |${\color{green}\textbf{2.143 - 2.209}}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | 4                      |${\color{green}1.146 - 2.178}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | 5                      |${\color{red}\textbf{3.142 - 5.012}}$|
|16 M                   | 0.5     | 0.2     | 0.3   | 0.5                            | 6                      |${\color{green}2.181 - 2.207}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | -                      |${\color{orange}2.928 - 3.018}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | 2                      |${\color{green}\textbf{2.445 - 2.510}}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | 3                      |${\color{red}5.353 - 6.057}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | 4                      |${\color{red}3.933 - 6.345}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | 5                      |${\color{red}\textbf{6.129 - 6.129}}$|
|17.5 M                 | 0.5     | 0.2     | 0.3   | 0.5                            | 6                      |${\color{red}4.146 - 6.486}$|

Info: Nu exista spatiu suficient pentru heap (${\color{red}java.lang.OutOfMemoryError: Java \thinspace heap\thinspace space}$) pentru generarea unui numar de subscriptii mai mare sau egal cu 17.5 M, ceea ce duce la performante scazute atunci cand se foloseste paralelizarea si cand eroarea nu apare.
