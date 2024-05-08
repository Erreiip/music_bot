cp -r ./music_bot/playlists/ .
#cp -r ./music_bot/songs/ .

rm -rf ./music_bot 
git clone https://github.com/Erreiip/music_bot.git
cd music_bot

cd ..
cp ./secret.txt ./music_bot

rm -rf ./playlists

screen -dmS Chef "cd music_bot" && "./gradlew run"