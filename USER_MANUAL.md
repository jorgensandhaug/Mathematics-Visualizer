Dette er en brukermanual for programmet.
Programmet består av tre hovedvinduer: variabeloversikten, 2D-grafikkfelt og 3D-grafikkfelt.
I tillegg tar programmet imot brukerinput gjennom et inputfield og gjennom egne menyer på toppen av vinduet.
Grafikkfeltenes funksjon er å fremvise de definerte variablene i hendholdsvis 2D og 3D.
Variabler defineres gjennom knapppene på toppen eller inputfeltet.
I inputfeltet defineres variabler gjennom å skrive <variabelnavn>=
Etterfulgt av en av følgende patterns:
v = [1.0,-5.7, ... ] for vektor
p = (1.0,-5.7, ... ) for punkt
c = 1.0 - 2i for et komplekst tall
n = -1.2 eller et aritmetisk uttrykk med en av programmets matematiske funksjoner* for et reelt tall [eksempel: m = cos(32+9)^2.3]
A = [-5,1.3 ... ;2,-4 ... -2.4,5] for matrise [eksempel: B = [1,2,3;4,5,6]
f(x) = x^2*cos(2*x+1) for funksjon

I tillegg dette er det mulig å definere nye variabler ved å sette inn definerte variable i en rekke funksjoner


Programmets definerte numeriske funksjoner er for øyeblikket:
 reell skalar til reell skalar: cos(x), sin(x), tan(x), log(x), abs(x)
 vektor, vektor til vektor: add(u,v), subtract(u,v), cross(u,v)
 vektor, vektor til reell skalar: dot(u,v), angle(u,v)
 vektor, reell skalar til vektor: scale(v,x)
 vektor, matrise til vektor: transform(v,A)
 matrise, matrise til matrise: product(A,B)
 matrise, vektor til vektor: solveLinSys(A,v)
 punkt, matrise til punkt: transform(p,A)
 punkt, punkt til punkt: add(p,q), subtract(p,q)
 komplesk skalar, komplex skalar til komplex skalar: add(z,w), multiply(z,w)
 kompleks skalar, reell skalar til kompleks skalar: pow(z,x)
 vektor til reell skalar: abs(v)
 matrise til matrise: inverse(A)
 funksjon, reell skalar, reell skalar til reell skalar: sum(f,a,b)
 funksjon til funksjon: derivative(f)

 Mange av funksjonene krever at inputen har rett dimensjon i henhold til matematiske konvensjoner.

 Variablene kan skjules, byttes farge på, slettes og endres ved å trykke på knappene ved siden av variabelnavnet.
 Her er det også mulig transformere enkelte variabeltyper med en matrise slik at en animasjon av dette vises i grafikkfeltet.

 Det er mulig å lagre programmets definerte variabler i en fil, for så å hente den opp senere. Dette gjøres ve då trykke "Save state", skrive inn filnavnet, for deretter å trykke "Ok".
 Filen hentes enkelt opp igjen gjennom å trykke "Load state", velge ønsket fil, for deretter å trykke "Ok".
