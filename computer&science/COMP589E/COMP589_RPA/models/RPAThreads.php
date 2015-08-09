<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "{{%Threads}}".
 *
 * @property integer $id
 * @property integer $pid
 * @property integer $memory
 * @property integer $time
 * @property integer $CPU
 * @property string $command
 * @property string $IP
 */
class RPAThreads extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return '{{%Threads}}';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['pid', 'memory', 'time', 'CPU', 'command', 'IP'], 'required'],
            [['pid', 'memory', 'time', 'CPU'], 'integer'],
            [['command'], 'string'],
            [['IP'], 'string', 'max' => 255]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'pid' => 'Pid',
            'memory' => 'Memory',
            'time' => 'Time',
            'CPU' => 'Cpu',
            'command' => 'Command',
            'IP' => 'Ip',
        ];
    }
}
