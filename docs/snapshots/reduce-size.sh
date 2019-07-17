# $1 path to the file

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters. Provide the path to one file."
    exit 1
fi

sed -i '/^ *$/d' $1
