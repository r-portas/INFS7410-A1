# Builds a zip

mkdir -p dist
cp README.md dist
cp pom.xml dist
cp Report.pdf dist
cp -r resources dist
cp -r src dist

zip -r 43560846_portas.zip dist

rm -rfv dist
