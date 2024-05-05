cp -r ./music_bot/playlists/ .
#cp -r ./music_bot/songs/ .

rm -rf ./music_bot 
git clone https://github.com/Erreiip/music_bot.git
cd music_bot
git checkout master

cd ..
cp ./secret.txt ./music_bot/src/main/java/discord_bot/

rm -rf ./playlists