<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "RPA_Threads".
 *
 * @property integer $id
 * @property integer $pid
 * @property integer $memory
 * @property string $time
 * @property string $CPU
 * @property string $command
 * @property string $IP
 * @property integer $status
 */
class RPAThreads extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'RPA_Threads';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['pid', 'memory', 'time', 'CPU', 'command', 'IP'], 'required'],
            [['pid', 'memory', 'status'], 'integer'],
            [['time', 'command'], 'string'],
            [['CPU', 'IP'], 'string', 'max' => 255]
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
            'status' => 'Status',
        ];
    }
}
